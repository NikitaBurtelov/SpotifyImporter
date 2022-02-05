package org.spotify.importer.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


/**
 * @author Nikita Burtelov
 * TODO Оптимизировать контроллер
 */
@Controller
class MainController {
    private var users: Users? = null
    private val bufferId: String? = null
    private var userDao: UserDao? = null
    private var spotifyConnector: SpotifyConnector? = null
    var context: ApplicationContext? = null

    //
    //    @Autowired
    //    public void setSpotifyConnector(SpotifyConnector spotifyConnector) {
    //        this.spotifyConnector = spotifyConnector;
    //    }
    @Autowired
    fun setSpotifyConnector(spotifyConnector: SpotifyConnector?) {
        this.spotifyConnector = spotifyConnector
    }

    @Autowired
    fun setUserDao(userDao: UserDao?) {
        this.userDao = userDao
    }

    @Autowired
    fun setContext(context: ApplicationContext?) {
        this.context = context
    }

    @Autowired
    fun setUsers(users: Users?) {
        this.users = users
    }

    @GetMapping
    fun inputIdPage(@ModelAttribute("user") user: User?): String {
        return "data_input"
    }

    //Авторизация пользователя. Получаем token авторизации
    @PostMapping("input")
    fun authorizationSpotify(@ModelAttribute("user") userBuffer: @Valid User?): String {
        return "redirect:" + spotifyConnector.getCodeUrl()
    }

    //Переадресация spotify
    @GetMapping("/callback")
    fun callback(request: HttpServletRequest): String {
        val code = request.getParameter("code")
        val user: User = context!!.getBean(User::class.java)
        Logger.getLogger("Input").info(code)
        spotifyConnector.setCode(code)
        //Получить id пользователя
        //TODO проверить на null
        val idUser: String = JsoupRequest.requestGetIdUserSpotify(spotifyConnector.getTokenSpotify())
        user.setId(idUser)
        userDao.addUser(user)
        return "index"
    }

    @GetMapping("index")
    fun mainpage(): String {
        return "index"
    } //TODO v.1
    //    @GetMapping
    //    public String inputIdPage(@ModelAttribute("user") User user) {
    //        return "data_input";
    //    }
    //
    //    @PostMapping("input")
    //    public String inputId(@ModelAttribute("user") @Valid User user,
    //                          BindingResult bindingResult) {
    //
    //        if (bindingResult.hasErrors()) {
    //            return "data_input";
    //        }
    //        User realUser = context.getBean(User.class);
    //        realUser.setId(user.getId());
    //        realUser.getSpotifyConnector().setUser_id(realUser.getId());
    //
    //        userDao.addUser(realUser);
    //
    //        return "redirect:"+ user.getId() +"/index";
    //    }
    //
    //    @GetMapping("/{id}/index")
    //    public String mainpage(@PathVariable("id") String id, Model model) {
    //        model.addAttribute("user", users.getUser(id));
    //        return "index";
    //    }
    //
    //    @GetMapping("/callback")
    //    public String callback(HttpServletRequest request, Model model) {
    //        String code = request.getParameter("code");
    //        User user = users.getUser(bufferId);
    //        SpotifyConnector spotifyConnector = users.getUser(bufferId).getSpotifyConnector();
    //
    //        spotifyConnector.setCode(code);
    //        spotifyConnector.runSpotifyImporter(user.getPlaylist());
    //
    //        model.addAttribute("url", user.getPlaylist().getUrl());
    //
    //        return "redirect:"+ user.getId() + "/index";
    //    }
    //
    //    @PostMapping("/{id}/index")
    //    public String upload(HttpServletRequest request,
    //                         Model model, @PathVariable String id) {
    //        User user = users.getUser(id);
    //        Playlist playlist = user.getPlaylist();
    //        bufferId = user.getId();
    ////
    ////        if (bindingResult.hasErrors()) {
    ////            return "index";
    ////        }
    //
    //        String urlPlaylistVK = request.getParameter("url");
    //        playlist.setUrl(urlPlaylistVK);
    //        VkConnector.setTrackVkPlaylist(playlist, playlist.getUrl());
    //        JsoupRequest.joinUrl();
    //
    //        model.addAttribute("user", user);
    //
    //        return "redirect:" + user.getSpotifyConnector().getCodeUrl();
    //    }
}