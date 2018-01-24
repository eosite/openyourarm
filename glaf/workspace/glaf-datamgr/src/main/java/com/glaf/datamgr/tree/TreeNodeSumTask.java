package com.glaf.datamgr.tree;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TreeNodeSumTask extends RecursiveTask<Double> {
	protected static Log logger = LogFactory.getLog(TreeNodeSumTask.class);

	private static final long serialVersionUID = 1L;

	protected TreeNode treeNode;

	protected AtomicInteger countNode = new AtomicInteger(0);

	public TreeNodeSumTask(TreeNode treeNode) {
		this.treeNode = treeNode;
	}

	protected Double compute() {
		countNode = new AtomicInteger(0);
		double sumValue = sum(treeNode);
		logger.debug(treeNode.getId() + "的总计算节点数目：" + countNode.get());
		return sumValue;
	}

	/**
	 * 逐级汇总
	 * 
	 * @param treeNode
	 * @return
	 */
	protected double sum(TreeNode treeNode) {
		if (treeNode.getChildren() != null && treeNode.getChildren().size() > 0) {
			double sumValue = 0;
			countNode.incrementAndGet();
			for (TreeNode child : treeNode.getChildren()) {
				sumValue += this.sum(child);// 计算下级节点，递归所有子孙节点
			}
			treeNode.setSumValue(sumValue);// 当前节点的值等于所有子节点的和
			TreeNode parent = treeNode.getParent();
			if (parent != null) {
				// 父节点的值等于当前节点的值+当前节点之子孙节点的累加值
				parent.setSumValue(parent.getValue() + treeNode.getValue() + treeNode.getSumValue());
			}
			if (sumValue <= 0) {
				sumValue = treeNode.getValue();
				treeNode.setSumValue(treeNode.getValue());
			}
			// logger.debug(treeNode.getId() + "->" + treeNode.getSumValue());
			return sumValue;
		} else {
			countNode.incrementAndGet();
			treeNode.setSumValue(treeNode.getValue());
			// logger.debug(treeNode.getId() + "->" + treeNode.getSumValue());
		}
		return treeNode.getSumValue();
	}

}
