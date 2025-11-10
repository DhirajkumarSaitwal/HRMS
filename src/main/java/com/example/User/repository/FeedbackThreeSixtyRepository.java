package com.example.User.repository;

import com.example.User.entity.FeedbackThreeSixty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackThreeSixtyRepository extends JpaRepository<FeedbackThreeSixty, Long> {

    // Employee received feedback
    List<FeedbackThreeSixty> findByRevieweeId(Long revieweeId);

    // Employee gave feedback
    List<FeedbackThreeSixty> findByReviewerId(Long reviewerId);
}
