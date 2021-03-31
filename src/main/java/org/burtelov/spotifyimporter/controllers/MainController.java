package org.burtelov.spotifyimporter.controllers;

import org.burtelov.spotifyimporter.model.musicdata.Playlist;
import org.burtelov.spotifyimporter.model.spotify.JsoupRequest;
import org.burtelov.spotifyimporter.model.spotify.SpotifyConnector;
import org.burtelov.spotifyimporter.model.users.User;
import org.burtelov.spotifyimporter.model.users.Users;
import org.burtelov.spotifyimporter.model.vk.VkConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Nikita Burtelov
 * TODO Оптимизировать контроллер
 */
@Controller
public class MainController {
    private Users users;
    private String bufferId;
    ApplicationContext context;
//
//    @Autowired
//    public void setSpotifyConnector(SpotifyConnector spotifyConnector) {
//        this.spotifyConnector = spotifyConnector;
//    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    public void setUsers(Users users) {
        this.users = users;
    }

    @GetMapping
    public String inputIdPage(@ModelAttribute("user") User user) {
        return "data_input";
    }

    @PostMapping("input")
    public String inputId(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "data_input";
        }
        User realUser = context.getBean(User.class);
        realUser.setId(user.getId());
        realUser.getSpotifyConnector().setUser_id(realUser.getId());

        users.addUser(realUser);

        return "redirect:"+ user.getId() +"/index";
    }

    @GetMapping("/{id}/index")
    public String mainpage(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", users.getUser(id));
        return "index";
    }

    @GetMapping("/callback")
    public String callback(HttpServletRequest request, Model model) {
        String code = request.getParameter("code");
        User user = users.getUser(bufferId);
        SpotifyConnector spotifyConnector = users.getUser(bufferId).getSpotifyConnector();

        spotifyConnector.setCode(code);
        spotifyConnector.runSpotifyImporter(user.getPlaylist());

        model.addAttribute("url", user.getPlaylist().getUrl());

        return "redirect:"+ user.getId() + "/index";
    }

    @PostMapping("/{id}/index")
    public String upload(HttpServletRequest request,
                         Model model, @PathVariable String id) {
        User user = users.getUser(id);
        Playlist playlist = user.getPlaylist();
        bufferId = user.getId();
//
//        if (bindingResult.hasErrors()) {
//            return "index";
//        }

        String urlPlaylistVK = request.getParameter("url");
        playlist.setUrl(urlPlaylistVK);
        VkConnector.setTrackVkPlaylist(playlist, playlist.getUrl());
        JsoupRequest.joinUrl();

        model.addAttribute("user", user);

        return "redirect:" + user.getSpotifyConnector().getCodeUrl();
    }
//
//    @GetMapping("/")
//    public String newPerson(@ModelAttribute("person") Person person) {
//        return "people/new";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("person") @Valid Person person,
//                         BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//        if (bindingResult.hasErrors()) {
//            return "people/edit";
//        }
//
//        personDao.update(id, person);
//
//        return "redirect:/people";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        personDao.delete(id);
//        return "redirect:/people";
//    }
}
