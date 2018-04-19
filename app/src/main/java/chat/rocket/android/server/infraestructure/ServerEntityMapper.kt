package com.goalify.chat.android.server.infraestructure

import com.goalify.chat.android.server.domain.model.Server
import com.goalify.chat.android.util.DataToDomain

class ServerEntityMapper : DataToDomain<ServerEntity, Server> {
    override fun translate(data: ServerEntity): Server {
        return Server(data.id, data.name, data.host, data.avatar)
    }
}
