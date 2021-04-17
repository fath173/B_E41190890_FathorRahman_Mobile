package com.fathor.latihan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void linear_layout(View view) {
        Intent intent = new Intent(MainActivity.this, linear_layout.class);
        startActivity(intent);
    }

    public void relative_layout(View view) {
        Intent intent = new Intent(MainActivity.this, relative_layout.class);
        startActivity(intent);
    }

    public void constraint_layout(View view) {
        Intent intent = new Intent(MainActivity.this, constraint_layout.class);
        startActivity(intent);
    }

    public void frame_layout(View view) {
        Intent intent = new Intent(MainActivity.this, frame_layout.class);
        startActivity(intent);
    }

    public void table_layout(View view) {
        Intent intent = new Intent(MainActivity.this, tabel_layout.class);
        startActivity(intent);
    }

    public void material(View view) {
        Intent intent = new Intent(MainActivity.this, material_design.class);
        startActivity(intent);
    }

    public void scrollview(View view) {
        Intent intent = new Intent(MainActivity.this, scroll_view.class);
        startActivity(intent);
    }

    public void scrollviewhz(View view) {
        Intent intent = new Intent(MainActivity.this, scroll_view_horizontal.class);
        startActivity(intent);
    }
}