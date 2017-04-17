package com.nghinv.textnote.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NghiNV on 12/04/2017.
 */

public class TextNote implements Serializable {
    private String objectId;
    private String note;
    private String title;
    private String ownerId;

    /* private Date date;*/

    public Date created;
    public Date updated;


    public TextNote() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
