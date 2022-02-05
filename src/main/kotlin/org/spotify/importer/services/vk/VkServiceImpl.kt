package org.spotify.importer.services.vk

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.FileReader
import java.io.IOException
import org.spotify.importer.dto.music.Playlist
import org.spotify.importer.dto.music.Track

object VkServiceImpl {
    private fun getJsonObject(filePath: String): String? {
        return try {
            (JSONParser().parse(FileReader(filePath)) as JSONObject)["token"].toString()
        } catch (fileNotFoundException: IOException) {
            fileNotFoundException.printStackTrace()
            null
        } catch (fileNotFoundException: ParseException) {
            fileNotFoundException.printStackTrace()
            null
        }
    }

    fun setTrackVkPlaylist(playlist: Playlist, urlPlaylist: String?) {
        try {
            val arrTitle = ArrayList<String>()
            val arrAuthor = ArrayList<String>()
            val JsoupListTitle: Elements
            val JsoupListAuthor: Elements
            val doc = Jsoup.connect(urlPlaylist!!)
                .referrer("http://www.google.com")
                .get()
            JsoupListTitle = doc.select("span.audio_row__title_inner._audio_row__title_inner")
            for (element in JsoupListTitle.select("*")) {
                arrTitle.add(element.text())
            }
            JsoupListAuthor = doc.select("div.audio_row__performers a:nth-child(1)")
            for (element in JsoupListAuthor.select("a")) {
                arrAuthor.add(element.text())
            }
            //add title, author
            for (i in arrTitle.indices) {
                playlist.track(Track(arrTitle[i], arrAuthor[i]))
            }
            playlist.title = doc.select("h1.AudioPlaylistSnippet__title--main").first()!!.text()
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }
}