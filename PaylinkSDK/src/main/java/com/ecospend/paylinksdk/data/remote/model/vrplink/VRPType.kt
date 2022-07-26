package com.ecospend.paylinksdk.data.remote.model.vrplink

import com.google.gson.annotations.SerializedName
import java.io.Serializable

enum class VRPType : Serializable {
    @SerializedName("Sweeping")
    Sweeping,

    @SerializedName("Vrp")
    Vrp
}
