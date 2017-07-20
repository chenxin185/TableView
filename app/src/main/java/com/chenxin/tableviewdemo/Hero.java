package com.chenxin.tableviewdemo;

/**
 * Created by chenxin on 2017/7/19.
 * O(∩_∩)O~
 */

public class Hero {

    private String name;
    private int kill;
    private int dead;

    public Hero(String name, int kill, int dead) {
        this.name = name;
        this.kill = kill;
        this.dead = dead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }
}
