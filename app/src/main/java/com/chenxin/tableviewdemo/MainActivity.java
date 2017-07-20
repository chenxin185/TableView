package com.chenxin.tableviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenxin.tableview.TableAdapter;
import com.chenxin.tableview.TableView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Hero> list;
    String[] heroNameList = new String[]{
            "魔蛇之拥", "卡西奥佩娅", "死亡颂唱者", "卡尔萨斯", "黑暗之女 安妮", "（Annie）皮城女警", "凯特琳", "（女警）寡妇制造者", "伊芙琳", "（EVE）瘟疫之源",
            "魔蛇之拥1 ", "卡西奥佩娅1 ", "死亡颂唱者1 ", "卡尔萨斯1 ", "黑暗之女 安妮1 ", "（Annie）皮城女警1 ", "凯特琳1 ", "（女警）寡妇制造者1 ", "伊芙琳1 ", "（EVE）瘟疫之源1 ",
            "魔蛇之拥2 ", "卡西奥佩娅2 ", "死亡颂唱者2 ", "卡尔萨斯2 ", "黑暗之女 安妮2 ", "（Annie）皮城女警2 ", "凯特琳2 ", "（女警）寡妇制造者2 ", "伊芙琳2 ", "（EVE）瘟疫之源2 ",
            "魔蛇之拥3 ", "卡西奥佩娅3 ", "死亡颂唱者3 ", "卡尔萨斯3 ", "黑暗之女 安妮3 ", "（Annie）皮城女警3 ", "凯特琳3 ", "（女警）寡妇制造者3 ", "伊芙琳3 ", "（EVE）瘟疫之源3 ",
            "魔蛇之拥4 ", "卡西奥佩娅4 ", "死亡颂唱者4 ", "卡尔萨斯4 ", "黑暗之女 安妮4 ", "（Annie）皮城女警4 ", "凯特琳4 ", "（女警）寡妇制造者4 ", "伊芙琳4 ", "（EVE）瘟疫之源4 ",
            "魔蛇之拥5 ", "卡西奥佩娅5 ", "死亡颂唱者5 ", "卡尔萨斯5 ", "黑暗之女 安妮5 ", "（Annie）皮城女警5 ", "凯特琳5 ", "（女警）寡妇制造者5 ", "伊芙琳5 ", "（EVE）瘟疫之源5 ",
            "魔蛇之拥6 ", "卡西奥佩娅6 ", "死亡颂唱者6 ", "卡尔萨斯6 ", "黑暗之女 安妮6 ", "（Annie）皮城女警6 ", "凯特琳6 ", "（女警）寡妇制造者6 ", "伊芙琳6 ", "（EVE）瘟疫之源6 ",
            "魔蛇之拥7 ", "卡西奥佩娅7 ", "死亡颂唱者7 ", "卡尔萨斯7 ", "黑暗之女 安妮7 ", "（Annie）皮城女警7 ", "凯特琳7 ", "（女警）寡妇制造者7 ", "伊芙琳7 ", "（EVE）瘟疫之源7 ",
            "魔蛇之拥8 ", "卡西奥佩娅8 ", "死亡颂唱者8 ", "卡尔萨斯8 ", "黑暗之女 安妮8 ", "（Annie）皮城女警8 ", "凯特琳8 ", "（女警）寡妇制造者8 ", "伊芙琳8 ", "（EVE）瘟疫之源8 ",
            "魔蛇之拥9 ", "卡西奥佩娅9 ", "死亡颂唱者9 ", "卡尔萨斯9 ", "黑暗之女 安妮9 ", "（Annie）皮城女警9 ", "凯特琳9 ", "（女警）寡妇制造者9 ", "伊芙琳9 ", "（EVE）瘟疫之源9 "
    };

    TableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableView = (TableView) findViewById(R.id.tableView);
        list = new ArrayList<>();
        for (int i = 0; i < heroNameList.length; i++) {
            list.add(new Hero(heroNameList[i], (int) (Math.random() * 10), (int) (Math.random() * 10)));
        }

        //目前还没搞清楚，因为如果在inflate的时候不加一层外层View的话，在measure的时候会有问题。
        // 比如说，这个地方，如果不加外层View的话，三个设置了weight = 1的TextView并不会平分LinearLayout的宽度。
        LinearLayout linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(p);
        tableView.addHeadView(LayoutInflater.from(this).inflate(R.layout.head, linearLayout));
        tableView.setAdapter(new MyAdapter());
    }


    class MyAdapter extends TableAdapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            holder.name.setText(list.get(position).getName());
            holder.kill.setText(list.get(position).getKill() + "");
            holder.dead.setText(list.get(position).getDead() + "");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            TextView kill;
            TextView dead;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                kill = (TextView) itemView.findViewById(R.id.kill);
                dead = (TextView) itemView.findViewById(R.id.dead);
            }
        }

    }


}
