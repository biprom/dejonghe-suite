package com.adverto.dejonghe.common.wizard.RVSplate;

public class ProductConfiguration {

    private Double length;
    private Double width;
    private Double thickness;

    private Double volume;

    private Processing processing;
    private Folded folded;

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getThickness() {
        return thickness;
    }

    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Processing getProcessing() {
        return processing;
    }

    public void setProcessing(Processing processing) {
        this.processing = processing;
    }

    public Folded getFolded() {
        return folded;
    }

    public void setFolded(Folded folded) {
        this.folded = folded;
    }

    @Override
    public String toString() {
        return "ProductConfiguration{" +
                "length=" + length +
                ", width=" + width +
                ", thickness=" + thickness +
                ", volume=" + volume +
                ", processing=" + processing +
                ", folded=" + folded +
                '}';
    }
}