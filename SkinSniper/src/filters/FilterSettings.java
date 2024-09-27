package filters;

import java.util.random.RandomGenerator;

public class FilterSettings {
    private String name;
    private Double max;
    private Double min;
    private int checkLast;
    private int volume;
    private boolean hideUnrelSkins;
    private boolean saveYellow;
    private Long tag;



	// Constructor
    public FilterSettings(String name, Double max, Double min, int checkLast, int volume, boolean hideUnrelSkins, boolean saveYellow) {
        this.name = name;
        this.max = max;
        this.min = min;
        this.checkLast = checkLast;
        this.volume = volume;
        this.hideUnrelSkins = hideUnrelSkins;
        this.saveYellow = saveYellow;
        this.tag = RandomGenerator.getDefault().nextLong();
    }
    
    public Long getTag() {
		return tag;
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}
    
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public int getCheckLast() {
        return checkLast;
    }

    public void setCheckLast(int checkLast) {
        this.checkLast = checkLast;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isHideUnrelSkins() {
        return hideUnrelSkins;
    }

    public void setHideUnrelSkins(boolean hideUnrelSkins) {
        this.hideUnrelSkins = hideUnrelSkins;
    }

    public boolean isSaveYellow() {
        return saveYellow;
    }

    public void setSaveYellow(boolean saveYellow) {
        this.saveYellow = saveYellow;
    }

    @Override
    public String toString() {
        return "FilterSettings{" +
                "name='" + name + '\'' +
                ", max=" + max +
                ", min=" + min +
                ", checkLast=" + checkLast +
                ", tag=" + tag +
                ", volume=" + volume +
                ", hideUnrelSkins=" + hideUnrelSkins +
                ", saveYellow=" + saveYellow +
                '}';
    }
}
