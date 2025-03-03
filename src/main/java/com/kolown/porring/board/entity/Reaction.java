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
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.io.Serializable;

@Entity
@Table(name = "reactions")
@Getter
@SoftDelete(columnName = "deleted")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reaction {
    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ReactionId implements Serializable {
        private Long boardId;
        private Long accountId;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || obj instanceof ReactionId) {
                return false;
            }

            return Objects.equals(boardId, ((ReactionId) obj).boardId) &&
                Objects.equals(accountId, ((ReactionId) obj).accountId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(boardId, accountId);
        }
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

    public Reaction(Board board, Account account, ReactionType reactionType) {
        this.id = new ReactionId(board.getId(), account.getId());
        this.board = board;
        this.account = account;
        this.reactionType = reactionType;
    }

    public void updateReaction(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
