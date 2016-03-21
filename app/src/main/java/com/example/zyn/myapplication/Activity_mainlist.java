package com.example.zyn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.List;

import com.example.zyn.myapplication.Activity_main;


/**
 * Created by ZYN on 2016/3/12.
 */
public class Activity_mainlist extends Activity{
    Button btn1,btn2;
    ExpandableListView expandableListView;
    TreeViewAdapter adapter;
    SuperTreeViewAdapter superAdapter;
    public String[] parent = { "扣件的功能"};
    public String[][][] child_grandchild = {
            { { "固定钢轨" }, { "维持轨距的公差范围", "调整轨道几何尺寸" } },
            { { "二型扣件" }, { "三型扣件", "Wj-扣件" } }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mainlist);
        btn1 = (Button) findViewById(R.id.Buttonfh);
        btn2 = (Button) findViewById(R.id.Buttontc);
        btn2.setOnClickListener(listener);
        expandableListView = (ExpandableListView) findViewById(R.id.listView);
        adapter = new TreeViewAdapter(this, 38);
        superAdapter = new SuperTreeViewAdapter(this, stvClickEvent);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_mainlist.this,Activity_main.class);
                startActivity(intent);
                Activity_mainlist.this.finish();

            }
        });
    }
    public View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            adapter.removeAll();
            adapter.notifyDataSetChanged();
            superAdapter.RemoveAll();
            superAdapter.notifyDataSetChanged();

            if (v == btn2) {
                List<SuperTreeViewAdapter.SuperTreeNode> superNodeTree = superAdapter
                        .GetTreeNode();
                for (int i = 0; i < parent.length; i++) {
                    SuperTreeViewAdapter.SuperTreeNode superNode = new SuperTreeViewAdapter.SuperTreeNode();
                    superNode.parent = parent[i];

                    for (int j = 0; j < child_grandchild.length; j++) {
                        TreeViewAdapter.TreeNode node = new TreeViewAdapter.TreeNode();
                        node.parent = child_grandchild[j][0][0];
                        for (int k = 0; k < child_grandchild[j][1].length; k++) {
                            node.childs.add(child_grandchild[j][1][k]);
                        }
                        superNode.childs.add(node);
                    }
                    superNodeTree.add(superNode);
                }
                superAdapter.UpdateTreeNode(superNodeTree);
                expandableListView.setAdapter(superAdapter);
            }
        }

    };

    ExpandableListView.OnChildClickListener stvClickEvent = new ExpandableListView.OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {
            // TODO Auto-generated method stub
            Intent intent=new Intent(Activity_mainlist.this,Activity_sfv.class);
            startActivity(intent);
            String msg = " 你选择了： " + child_grandchild[groupPosition ] + "  "
                    + childPosition;
            Toast.makeText(Activity_mainlist.this, msg,
                    Toast.LENGTH_SHORT);

            return false;
        }
    };
}
