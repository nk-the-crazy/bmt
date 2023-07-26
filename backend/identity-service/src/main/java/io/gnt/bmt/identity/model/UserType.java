package io.gnt.bmt.identity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    SYSTEM(1),
    REGULAR(2);

    private int id;
}