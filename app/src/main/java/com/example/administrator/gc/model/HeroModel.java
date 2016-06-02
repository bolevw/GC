package com.example.administrator.gc.model;

import com.example.administrator.gc.base.BaseModel;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroModel extends BaseModel {
    private int nodetype;
    private String tag;
    private Attr attr;

    @Override
    public String toString() {
        return "HeroModel{" +
                "nodetype=" + nodetype +
                ", tag='" + tag + '\'' +
                ", attr=" + attr +
                '}';
    }

    public int getNodetype() {
        return nodetype;
    }

    public void setNodetype(int nodetype) {
        this.nodetype = nodetype;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public class Attr {
        public String src;
        public String alt;
        public String title;

        @Override
        public String toString() {
            return "Attr{" +
                    "src='" + src + '\'' +
                    ", alt='" + alt + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
