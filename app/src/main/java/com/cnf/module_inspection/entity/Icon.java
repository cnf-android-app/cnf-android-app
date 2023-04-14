package com.cnf.module_inspection.entity;

import java.util.Objects;

public class Icon {

    private int iconID;
    private String name;
    private String styleClass;
    private String fontAwesome;
    private String materialIcon;
    private Boolean active;

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getFontAwesome() {
        return fontAwesome;
    }

    public void setFontAwesome(String fontAwesome) {
        this.fontAwesome = fontAwesome;
    }

    public String getMaterialIcon() {
        return materialIcon;
    }

    public void setMaterialIcon(String materialIcon) {
        this.materialIcon = materialIcon;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.iconID;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.styleClass);
        hash = 73 * hash + Objects.hashCode(this.fontAwesome);
        hash = 73 * hash + Objects.hashCode(this.materialIcon);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Icon other = (Icon) obj;
        if (this.iconID != other.iconID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.styleClass, other.styleClass)) {
            return false;
        }
        if (!Objects.equals(this.fontAwesome, other.fontAwesome)) {
            return false;
        }
        if (!Objects.equals(this.materialIcon, other.materialIcon)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        return true;
    }

}
