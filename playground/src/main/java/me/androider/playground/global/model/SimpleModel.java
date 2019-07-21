package me.androider.playground.global.model;

public class SimpleModel {

    private Integer number;

    private String text;

    public SimpleModel() {

    }

    public SimpleModel(Integer number, String text) {
        this.number = number;
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class Builder {
        SimpleModel model;

        public Builder setNumber(Integer number) {
            model.setNumber(number);
            return this;
        }

        public Builder setText(String text) {
            model.setText(text);
            return this;
        }

        public SimpleModel build(){
            return model;
        }
    }
}
