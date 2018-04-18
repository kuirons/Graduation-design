/* Copyright (c) 2009 Mustafa OZCAN (http://www.mustafaozcan.net)
 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 * Version: 1.0.2
 * Requires: jquery.1.3+
 */
jQuery.fn.fixedtableheader = function(options) {
	var settings = jQuery.extend({
		headerrowsize: 1,
		highlightrow: false,
		highlightclass: "highlight",
		scrollElement:'div.ui-layout-center'
	},
	options);
	this.each(function(i) {
		var $tbl = $(this);
		var $tblhfixed = $tbl.find("tr:lt(" + settings.headerrowsize + ")");
		var baseelmtop = $tblhfixed.offset().top;
		var headerelement = "th";
		if ($tblhfixed.find(headerelement).length == 0) headerelement = "td";
		if ($tblhfixed.find(headerelement).length > 0) {
			$tblhfixed.find(headerelement).each(function() {
				$(this).css("width", $(this).width());
			});
			var $clonedTable = $tbl.clone().empty();
			var tblwidth = GetTblWidth($tbl);
			var clonedtableid = "clonedtablehead"+new Date().getTime();
			$clonedTable.attr("id", clonedtableid + i).css({
				"position": "fixed",
				"top": "0",
				"left": $tbl.offset().left
			}).append($tblhfixed.clone()).width(tblwidth).hide().appendTo($("body"));
			if (settings.highlightrow) $("tr:gt(" + (settings.headerrowsize - 1) + ")", $tbl).hover(function() {
				$(this).addClass(settings.highlightclass);
			},
			function() {
				$(this).removeClass(settings.highlightclass);
			});
			$(settings.scrollElement).scroll(function() {
				$tblhfixed = $tbl.find("tr:lt(" + settings.headerrowsize + ")");
				 $clonedTable.css({
					"position": "fixed",
					"top": "0",
					"left": $tbl.offset().left - $(settings.scrollElement).scrollLeft()
				});
				var sctop = $(settings.scrollElement).scrollTop();
				var elmtop = $tblhfixed.offset().top;
				console.log("basetop:"+baseelmtop+"  elemtop:"+elmtop +"  sctop:"+sctop+" height:"+$tbl.height()+" fheight:"+$tblhfixed.height());
				console.log($tblhfixed.offset());
				if (sctop >= Math.abs(elmtop)+baseelmtop && sctop <= (Math.abs(elmtop)+baseelmtop + $tbl.height() - $tblhfixed.height())) $clonedTable.show();
				else $clonedTable.hide();
			});
			$(settings.scrollElement).resize(function() {
				if ($clonedTable.outerWidth() != $tbl.outerWidth()) {
					$tblhfixed = $tbl.find("tr:lt(" + settings.headerrowsize + ")");
					$tblhfixed.find(headerelement).each(function(index) {
						var w = $(this).width();
						$(this).css("width", w);
						$clonedTable.find(headerelement).eq(index).css("width", w);
					});
					$clonedTable.width($tbl.outerWidth());
				}
				$clonedTable.css("left", $tbl.offset().left);
			});
		}
	});
	function GetTblWidth($tbl) {
		var tblwidth = $tbl.outerWidth();
		return tblwidth;
	}
};