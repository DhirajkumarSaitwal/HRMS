package com.example.User.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "feedback_three_sixty")
public class FeedbackThreeSixty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    // reviewer (who gives feedback) -> Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","attendances","documents","user","reportingManager","hrbp"})
    private Employee reviewer;

    // reviewee (who receives feedback) -> Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","attendances","documents","user","reportingManager","hrbp"})
    private Employee reviewee;

    @Column(length = 2000)
    private String comments;

    // numeric rating (scale 0 - 10 or 0-100 as you prefer)
    @Column(precision = 5, scale = 2)
    private BigDecimal rating;

    // soft-delete / active flag
    @Column(name = "is_active")
    private Boolean isActive = true;

    // audit
    @Column(updatable = false)
    private String createdBy;

    private String updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.isActive == null) this.isActive = true;
        if (this.createdBy == null) this.createdBy = "ADMIN";
        if (this.updatedBy == null) this.updatedBy = "ADMIN";
    }

    @PreUpdate
    public void preUpdate() {
        if (this.updatedBy == null) this.updatedBy = "ADMIN";
    }
}
