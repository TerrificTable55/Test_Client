package xyz.terrifictable.setting.settings;

import xyz.terrifictable.setting.Setting;

public class NumberSetting extends Setting {

    public double value;
    public double min;
    public double max;
    public double increment;

    public NumberSetting(String name, double value, double min, double max, double increment) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        double precision = 1 / increment;

        this.value = Math.round(Math.max(min, Math.min(max, value)) * precision) / precision;
    }
    public void increment(boolean positive) {
        setValue(getValue() + (positive ? 1 : -1) * increment);
    }
    public double getMin() {
        return min;
    }
    public void setMin(double min) {
        this.min = min;
    }
    public double getMax() {
        return max;
    }
    public void setMax(double max) {
        this.max = max;
    }
    public double getIncrement() {
        return increment;
    }
    public void setIncrement(double increment) {
        this.increment = increment;
    }
}
