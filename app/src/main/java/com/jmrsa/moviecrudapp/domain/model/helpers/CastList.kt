package com.jmrsa.moviecrudapp.domain.model.helpers

import com.jmrsa.moviecrudapp.data.remote.dto.Cast

data class CastList(
    val castList: MutableList<Cast>?
)