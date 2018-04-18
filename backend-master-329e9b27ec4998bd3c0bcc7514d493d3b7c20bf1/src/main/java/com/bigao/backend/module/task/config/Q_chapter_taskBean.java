package com.bigao.backend.module.task.config;

import com.bigao.backend.common.config.BaseBean;

/**
 * Created by wait on 2015/12/15.
 */
public class Q_chapter_taskBean extends BaseBean {
    private int q_id;
    private int q_sn;
    private String q_name;
    private String q_desc;
    private int q_chapter_id;
    private String q_chapter;
    private String q_targets;
    private String q_prize;
    private String q_weapon;
    private String q_start_dialog;
    private int q_acc_npc_hide;
    private int q_acc_npc_show;
    private int q_end_npc_hide;
    private int q_end_npc_show;
    private int q_acc_monster_hide;
    private int q_acc_monster_show;
    private int q_end_monster_hide;
    private int q_end_monster_show;
    private int q_npc_follow;
    private String q_npc_bust_id;
    private String q_npc_bust_name;
    private String q_npc_bust_type;
    private String q_npc_bust_dialogue;
    private int q_shake_time;
    private int q_shake_frequency;
    private int q_shake_radio;
    private int q_music;
    private int q_map_to;
    private int q_x_to;
    private int q_y_to;
    private String q_guaji;
    private int q_copy_type;
    private int q_patrol;
    private int q_hp;
    private int q_attack;
    private int q_defence;
    private int q_crit;
    private int q_dodge;

    public int read(String[] array) {
        int sIndex = 0;
        this.q_id = getInt(array, sIndex);
        this.q_sn = getInt(array, sIndex + 1);
        this.q_name = getString(array, sIndex + 2);
        this.q_desc = getString(array, sIndex + 3);
        this.q_chapter_id = getInt(array, sIndex + 4);
        this.q_chapter = getString(array, sIndex + 5);
        this.q_targets = getString(array, sIndex + 6);
        this.q_prize = getString(array, sIndex + 7);
        this.q_weapon = getString(array, sIndex + 8);
        this.q_start_dialog = getString(array, sIndex + 9);
        this.q_acc_npc_hide = getInt(array, sIndex + 10);
        this.q_acc_npc_show = getInt(array, sIndex + 11);
        this.q_end_npc_hide = getInt(array, sIndex + 12);
        this.q_end_npc_show = getInt(array, sIndex + 13);
        this.q_acc_monster_hide = getInt(array, sIndex + 14);
        this.q_acc_monster_show = getInt(array, sIndex + 15);
        this.q_end_monster_hide = getInt(array, sIndex + 16);
        this.q_end_monster_show = getInt(array, sIndex + 17);
        this.q_npc_follow = getInt(array, sIndex + 18);
        this.q_npc_bust_id = getString(array, sIndex + 19);
        this.q_npc_bust_name = getString(array, sIndex + 20);
        this.q_npc_bust_type = getString(array, sIndex + 21);
        this.q_npc_bust_dialogue = getString(array, sIndex + 22);
        this.q_shake_time = getInt(array, sIndex + 23);
        this.q_shake_frequency = getInt(array, sIndex + 24);
        this.q_shake_radio = getInt(array, sIndex + 25);
        this.q_music = getInt(array, sIndex + 26);
        this.q_map_to = getInt(array, sIndex + 27);
        this.q_x_to = getInt(array, sIndex + 28);
        this.q_y_to = getInt(array, sIndex + 29);
        this.q_guaji = getString(array, sIndex + 30);
        this.q_copy_type = getInt(array, sIndex + 31);
        this.q_patrol = getInt(array, sIndex + 32);
        this.q_hp = getInt(array, sIndex + 33);
        this.q_attack = getInt(array, sIndex + 34);
        this.q_defence = getInt(array, sIndex + 35);
        this.q_crit = getInt(array, sIndex + 36);
        this.q_dodge = getInt(array, sIndex + 37);
        return sIndex + 38;
    }

    public int getQ_id() {
        return q_id;
    }

    public int getQ_sn() {
        return q_sn;
    }

    public String getQ_name() {
        return q_name;
    }

    public String getQ_desc() {
        return q_desc;
    }

    public int getQ_chapter_id() {
        return q_chapter_id;
    }

    public String getQ_chapter() {
        return q_chapter;
    }

    public String getQ_targets() {
        return q_targets;
    }

    public String getQ_prize() {
        return q_prize;
    }

    public String getQ_weapon() {
        return q_weapon;
    }

    public String getQ_start_dialog() {
        return q_start_dialog;
    }

    public int getQ_acc_npc_hide() {
        return q_acc_npc_hide;
    }

    public int getQ_acc_npc_show() {
        return q_acc_npc_show;
    }

    public int getQ_end_npc_hide() {
        return q_end_npc_hide;
    }

    public int getQ_end_npc_show() {
        return q_end_npc_show;
    }

    public int getQ_acc_monster_hide() {
        return q_acc_monster_hide;
    }

    public int getQ_acc_monster_show() {
        return q_acc_monster_show;
    }

    public int getQ_end_monster_hide() {
        return q_end_monster_hide;
    }

    public int getQ_end_monster_show() {
        return q_end_monster_show;
    }

    public int getQ_npc_follow() {
        return q_npc_follow;
    }

    public String getQ_npc_bust_id() {
        return q_npc_bust_id;
    }

    public String getQ_npc_bust_name() {
        return q_npc_bust_name;
    }

    public String getQ_npc_bust_type() {
        return q_npc_bust_type;
    }

    public String getQ_npc_bust_dialogue() {
        return q_npc_bust_dialogue;
    }

    public int getQ_shake_time() {
        return q_shake_time;
    }

    public int getQ_shake_frequency() {
        return q_shake_frequency;
    }

    public int getQ_shake_radio() {
        return q_shake_radio;
    }

    public int getQ_music() {
        return q_music;
    }

    public int getQ_map_to() {
        return q_map_to;
    }

    public int getQ_x_to() {
        return q_x_to;
    }

    public int getQ_y_to() {
        return q_y_to;
    }

    public String getQ_guaji() {
        return q_guaji;
    }

    public int getQ_copy_type() {
        return q_copy_type;
    }

    public int getQ_patrol() {
        return q_patrol;
    }

    public int getQ_hp() {
        return q_hp;
    }

    public int getQ_attack() {
        return q_attack;
    }

    public int getQ_defence() {
        return q_defence;
    }

    public int getQ_crit() {
        return q_crit;
    }

    public int getQ_dodge() {
        return q_dodge;
    }
}