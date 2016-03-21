package com.example.zyn.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import com.example.zyn.myapplication.TreeViewAdapter.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class SuperTreeViewAdapter extends BaseExpandableListAdapter {

	static public class SuperTreeNode {
		Object parent;
		//�������β˵��Ľṹ��
		List<TreeViewAdapter.TreeNode> childs = new ArrayList<TreeViewAdapter.TreeNode>();
	}

	private List<SuperTreeNode> superTreeNodes = new ArrayList<SuperTreeNode>();
	private Context parentContext;
	private OnChildClickListener stvClickEvent;//�ⲿ�ص�����
	
	public SuperTreeViewAdapter(Context view,OnChildClickListener stvClickEvent) {
		parentContext = view;
		this.stvClickEvent=stvClickEvent;
	}

	public List<SuperTreeNode> GetTreeNode() {
		return superTreeNodes;
	}

	public void UpdateTreeNode(List<SuperTreeNode> node) {
		superTreeNodes = node;
	}
	
	public void RemoveAll()
	{
		superTreeNodes.clear();
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		return superTreeNodes.get(groupPosition).childs.get(childPosition);
	}

	public int getChildrenCount(int groupPosition) {
		return superTreeNodes.get(groupPosition).childs.size();
	}

	public ExpandableListView getExpandableListView() {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, TreeViewAdapter.ItemHeight);
		ExpandableListView superTreeView = new ExpandableListView(parentContext);
		superTreeView.setLayoutParams(lp);
		return superTreeView;
	}

	/**
	 * �������ṹ�еĵڶ�����һ��ExpandableListView
	 */	
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// �� 
		final ExpandableListView treeView = getExpandableListView();
		final TreeViewAdapter treeViewAdapter = new TreeViewAdapter(this.parentContext,0);
		List<TreeNode> tmp = treeViewAdapter.getTreeNode();//��ʱ����ȡ��TreeViewAdapter��TreeNode���ϣ���Ϊ��
		final TreeNode treeNode=(TreeNode) getChild(groupPosition, childPosition);
		tmp.add(treeNode);
		treeViewAdapter.updateTreeNode(tmp);
		treeView.setAdapter(treeViewAdapter);
		
		//�ؼ��㣺ȡ��ѡ�еĶ������β˵��ĸ��ӽڵ�,������ظ��ⲿ�ص�����
		treeView.setOnChildClickListener(this.stvClickEvent);
		
		/**
		 * �ؼ��㣺�ڶ����˵�չ��ʱͨ��ȡ�ýڵ��������õ������˵��Ĵ�С
		 */
		treeView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT,
						(treeNode.childs.size()+1)*TreeViewAdapter.ItemHeight + 10);
				treeView.setLayoutParams(lp);
			}
		});
		
		/**
		 * �ڶ����˵�����ʱ����Ϊ��׼Item��С
		 */
		treeView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
						TreeViewAdapter.ItemHeight);
				treeView.setLayoutParams(lp);
			}
		});
		treeView.setPadding(TreeViewAdapter.PaddingLeft*2, 0, 0, 0);
		return treeView;
	}

	/**
	 * �������ṹ�е��ײ���TextView,������Ϊtitle
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView textView = TreeViewAdapter.getTextView(this.parentContext);
		textView.setText(getGroup(groupPosition).toString());
		textView.setPadding(TreeViewAdapter.PaddingLeft*2, 0, 0, 0);
		return textView;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public Object getGroup(int groupPosition) {
		return superTreeNodes.get(groupPosition).parent;
	}

	public int getGroupCount() {
		return superTreeNodes.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}
}
