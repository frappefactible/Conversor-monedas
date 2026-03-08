package com.conversor.dto;

import com.google.gson.annotations.SerializedName;

public record RespuestaMoneda(
        @SerializedName("result") String resultado,
        @SerializedName("base_code") String monedaBase,
        @SerializedName("target_code") String monedaObjetivo,
        @SerializedName("conversion_rate") double tasaDeConversion
) {
}
