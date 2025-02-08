package com.kolown.porring.board.entity;

import com.kolown.porring.account.entity.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.io.Serializable;

@Entity
@Table(name = "reactions")
@Getter
@SoftDelete(columnName = "deleted")
public class Reaction {
    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReactionId implements Serializable {
        private Long boardId;
        private Long accountId;
    }

    @EmbeddedId
    private ReactionId id;

    @MapsId("boardId")
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @MapsId("accountId")
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "react_code")
    private ReactionType reactionType;

    @Column(name = "deleted", columnDefinition = "TINYINT(1) default 0")
    private boolean deleteFlag;

}
