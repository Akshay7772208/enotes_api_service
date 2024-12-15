package com.pvt.enotes.dto;

import com.pvt.enotes.entity.Notes;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FavouriteNoteDto {

    private Integer id;

    private NotesDto note;

    private Integer userId;
}
