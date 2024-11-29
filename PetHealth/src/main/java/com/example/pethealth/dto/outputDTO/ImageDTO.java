package com.example.pethealth.dto.outputDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    @JsonProperty("post_id")
    private Long user_id;

    @JsonProperty("comment_id")
    private Long comment_id;

    @JsonProperty("pet_id")
    private Long pet_id;

    @JsonProperty("product_id")
    private Long product_id;


    @JsonProperty("url")
    private String url;
}
