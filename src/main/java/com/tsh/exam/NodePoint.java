package com.tsh.exam;

public class NodePoint {
    private Integer xIndex;
    private Integer yIndex;
    private Integer model;//1顺时,2逆时
    private Integer direction;//1右,2下,3左,4上
    private Integer step;//步数

    public NodePoint(){

    }

    public NodePoint(Integer xIndex, Integer yIndex, Integer model, Integer direction, Integer step) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.model = model;
        this.direction = direction;
        this.step = step;
    }

    public void changeDirection(){
    	switch (direction){
        case 1:
        	if(model == 1){
        		direction = 2;
        	}else{
        		direction = 4;
        	}
            break;
        case 2:
        	if(model == 1){
        		direction = 3;
        	}else{
        		direction = 1;
        	}
            break;
        case 3:
        	if(model == 1){
        		direction = 4;
        	}else{
        		direction = 2;
        	}
            break;
        case 4:
        	if(model == 1){
        		direction = 1;
        	}else{
        		direction = 3;
        	}
            break;
    	}
    }
    
    /**
     *
     * @param type  1前进,-1后退
     */
    public void go(int type){
        Integer curModel = type==-1 ? 1 : 2;
        if(direction==4){
            if(curModel==1){
                yIndex++;
            }else{
                yIndex--;
            }
        }else if(direction==1){
            if(curModel==1){
                yIndex--;
            }else{
                yIndex++;
            }
        }else if(direction==2){
            if(curModel==1){
                xIndex--;
            }else{
                xIndex++;
            }
        }else if(direction==3){
            if(curModel==1){
                xIndex++;
            }else{
                xIndex--;
            }
        }
        if(type==-1){
            step--;
        }else{
            step++;
        }
    }

    //回退
    public void back(){
        go(-1);
    }

    //前进
    public void forward(){
        go(1);
    }

    public Integer getxIndex() {
        return xIndex;
    }

    public void setxIndex(Integer xIndex) {
        this.xIndex = xIndex;
    }

    public Integer getyIndex() {
        return yIndex;
    }

    public void setyIndex(Integer yIndex) {
        this.yIndex = yIndex;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
