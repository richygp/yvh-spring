package com.seedtag.codetest.yvh.dto;

import com.seedtag.codetest.yvh.model.EnemyType;

import java.util.Map;

public class EnemyDTO {
    private final EnemyType type;
    private final int number;

    public EnemyDTO(String type, int number) {
        Map<String, EnemyType> enemyMapper = Map.of(
                "soldier", EnemyType.SOLDIER,
                "mech", EnemyType.MECH);
        this.type = enemyMapper.get(type);
        this.number = number;
    }

    public EnemyType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }
}
