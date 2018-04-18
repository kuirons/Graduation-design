package com.bigao.backend.db;

/**
 * Created by wait on 2015/12/29.
 */
public class Template {
    private int id;
    private String res_url;
    private String flash_url;
    private String mode;
    private String db_usr;
    private String db_pwd;
    private String note;

    public static Template crossTemplate() {
        Template t = new Template();
        t.id = Server.CROSS_TEMPLATE_ID;
        t.res_url = "http://192.168.1.50/debug";
        t.flash_url = "http://192.168.1.100:81/xy_cehua_temp/index.html";
        t.mode = "DEBUG";
        t.db_usr = "game";
        t.db_pwd = "game";
        t.note = "跨服";
        return t;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRes_url() {
        return res_url;
    }

    public void setRes_url(String res_url) {
        this.res_url = res_url;
    }

    public String getFlash_url() {
        return flash_url;
    }

    public void setFlash_url(String flash_url) {
        this.flash_url = flash_url;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDb_usr() {
        return db_usr;
    }

    public void setDb_usr(String db_usr) {
        this.db_usr = db_usr;
    }

    public String getDb_pwd() {
        return db_pwd;
    }

    public void setDb_pwd(String db_pwd) {
        this.db_pwd = db_pwd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Template{");
        sb.append("id=").append(id);
        sb.append(", res_url=").append(res_url);
        sb.append(", flash_url=").append(flash_url);
        sb.append(", mode=").append(mode);
        sb.append(", db_usr=").append(db_usr);
        sb.append(", db_pwd=").append(db_pwd);
        sb.append(", note='").append(note);
        sb.append('}');
        return sb.toString();
    }
}
