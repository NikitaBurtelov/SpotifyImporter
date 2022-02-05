package org.spotify.importer.dto.music

import org.hibernate.validator.constraints.URL
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.constraints.NotEmpty

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class Playlist {
    val tracks = LinkedList<Track>()
    var title: String? = null
    var id: String? = null
    var url: @NotEmpty(message = "Url must not be empty") @URL String? = null

    fun track(track: Track) {
        tracks.add(track)
    }

    fun delete() {
        tracks.clear()
    }
}