package com.publicuhc.tsverificationchecker.models;

import java.util.Date;

/**
 * Adds the createdAt and updatedAt fields used by every model in the database
 */
public class APIModel {

    private Date m_createdAt;
    private Date m_updatedAt;

    public Date getUpdatedAt() {
        return m_updatedAt;
    }

    public Date getCreatedAt() {
        return m_createdAt;
    }

    public APIModel setUpdatedAt(Date date) {
        m_updatedAt = date;
        return this;
    }

    public APIModel setCreatedAt(Date date) {
        m_createdAt = date;
        return this;
    }
}
