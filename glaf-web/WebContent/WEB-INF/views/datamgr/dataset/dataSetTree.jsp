<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div id="dataset-01" class='mt-tree' style="width: auto; height: auto;">
	产品正在完善中...</div>
<script>
	$(function() {
		$(window).resize(resize);

		resize();
	});

	function resize() {
		$(".mt-tree").each(function() {
			$(this).css({
				height : $(document).height() * 0.88
			});
		});

		$("#tabstrip").closest("div[role=group]").css({
			height : $(document).height()
		});
	}
</script>