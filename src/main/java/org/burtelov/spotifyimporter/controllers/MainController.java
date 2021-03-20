package org.burtelov.spotifyimporter.controllers;

import org.burtelov.spotifyimporter.model.musicdata.Playlist;
import org.burtelov.spotifyimporter.model.spotify.JsoupRequest;
import org.burtelov.spotifyimporter.model.spotify.SpotifyConnector;
import org.burtelov.spotifyimporter.model.user.User;
import org.burtelov.spotifyimporter.model.vk.VkConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Nikita Burtelov
 */
@Controller
public class MainController {
    private Playlist playlist;
    private SpotifyConnector spotifyConnector;
    @Autowired
    public void setSpotifyConnector(SpotifyConnector spotifyConnector) {
        this.spotifyConnector = spotifyConnector;
    }
    @Autowired
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @GetMapping
    public String inputIdPage(@ModelAttribute("user") User user) {
        return "data_input";
    }

    @PostMapping("input")
    public String inputId(@ModelAttribute("user") User user) {

        spotifyConnector.setUser_id(user.getId());
        System.out.println(user.getId());

        return "redirect:/index";
    }

    @GetMapping("index")
    public String mainpage() {
        return "index";
    }

    @GetMapping("/callback")
    public String callback(HttpServletRequest request, Model model) {
        String code = request.getParameter("code");
        System.out.println(code);

        spotifyConnector.setCode(code);
        spotifyConnector.runSpotifyImporter(playlist);

        model.addAttribute("url", playlist.getUrl());

        return "index";
    }
    @PostMapping("/callback")
    public String callbackPost() {
        return "index";
    }

    @PostMapping("index")
    public String upload(HttpServletResponse response,HttpServletRequest request, Model model) {
        String urlPlaylistVK = request.getParameter("url");
        playlist.setUrl(urlPlaylistVK);
        VkConnector.setTrackVkPlaylist(playlist, playlist.getUrl());
        JsoupRequest.joinUrl();

        spotifyConnector.getCodeUrl();

        model.addAttribute("url", urlPlaylistVK);

        return "redirect:https://accounts.spotify.com/authorize?client_id=c700db30083545a6a05352d0304a0597&response_type=code&scope=playlist-modify-public+playlist-modify-private&redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fcallback";
    }
}
