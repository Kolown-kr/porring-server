package com.kolown.porring.img;


import lombok.Getter;

@Getter
public class ImageUploadResponseDTO {

    private String imageUrl;

    public ImageUploadResponseDTO(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
