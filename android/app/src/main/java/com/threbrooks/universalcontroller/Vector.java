package com.threbrooks.universalcontroller;


public class Vector {

    private float x;
    private float y;
    private float z;

    public Vector(float _x, float _y, float _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    public float norm() {
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public float dotProduct(Vector v) {
        return v.x*x+v.y*y+v.z*z;
    }

    float getAngleToVector(Vector v) {
        return (float)Math.acos(dotProduct(v)/(norm()*v.norm()));
    }
}
