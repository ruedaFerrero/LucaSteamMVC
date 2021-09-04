/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mvc.spring.model;

/**
 * QueryInfo
 * La clase Query Info modela una búsqueda del usuario en el sistema
 * @author miso
 * @version 1.0, Septiembre, 2021
 */
public class QueryInfo {
    private String option = "name";
    private String text;

    public QueryInfo() {
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "option='" + option + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
