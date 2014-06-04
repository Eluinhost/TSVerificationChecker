package com.publicuhc.tsverificationchecker.models;

import java.util.Date;

/**
 * Adds the createdAt and updatedAt fields used by every model in the database
 */
public class APIModel {

    private final Date m_createdAt;
    private final Date m_updatedAt;

    public APIModel(Date createdAt, Date updatedAt) {
        m_createdAt = createdAt;
        m_updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return m_updatedAt;
    }

    public Date getCreatedAt() {
        return m_createdAt;
    }
}
