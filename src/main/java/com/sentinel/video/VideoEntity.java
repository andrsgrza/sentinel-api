package com.sentinel.video;

import com.sentinel.account.AccountEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "videos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Column(nullable = false)
    private String platform;

    @Column(name = "external_video_id", nullable = false)
    private String externalVideoId;

    @Column(nullable = false)
    private String url;

    private String title;

    private String description;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    private Long views;

    private Long likes;

    private Long comments;

    private Long shares;

    private Long saves;

    private String hashtags;

    @Column(name = "source_keyword")
    private String sourceKeyword;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}