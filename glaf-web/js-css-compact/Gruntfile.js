'use strict';

module.exports = function(grunt) {
	//require('load-grunt-tasks')(grunt);
	// css 样式文件打包
	var cssFiles = [
		"<%= config.sources %>/scripts/defineKendouibt.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css",
		"<%= config.sources %>/scripts/jquery-ui.min.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css",
		"<%= config.sources %>/scripts/designer/bootstrap_extend.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/pagination/css/pagination.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/touchspin/css/bootstrap.touchspin.min.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/tagsinput/bootstrap-tagsinput.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/menu/ext/megamenu.extends.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/datepicker/css/bootstrap-datepicker3.min.css",
		"<%= config.sources %>/scripts/designer/animate.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css",
		//"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/minimal/_all.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/square/_all.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/flat/_all.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/line/_all.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/polaris/polaris.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/futurico/futurico.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/grid/css/grid.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/css/bootstrap-select.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/treelist/css/treelist.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/definedCard/css/definedCard.css",
		"<%= config.sources %>/scripts/plugins/bootstrap/definedPanel/definedPanel.extend.css",
        "<%= config.sources %>/scripts/plugins/bootstrap/imageview/css/imageview.css",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	],
	// js 样式文件打包
	jsFiles = [
		"<%= config.sources %>/scripts/jquery.min.js",
		"<%= config.sources %>/scripts/jquery.PrintArea.js",
		"<%= config.sources %>/scripts/debug/mtdebug.js",
		"<%= config.sources %>/scripts/jquery.mtResizable.js",
		"<%= config.sources %>/scripts/resize/jquery-resize.min.js",
		"<%= config.sources %>/scripts/bootstrap/js/bootstrap.min.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js",
		"<%= config.sources %>/scripts/uuid.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/textbox/ext/jquery.textbox.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/touchspin/ext/jquery.touchspin.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/touchspin/ext/jquery.touchspin.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/tagsinput/bootstrap-tagsinput.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/textarea/ext/jquery.textarea.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/progressbar/ext/jquery.progressbar.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/menu/ext/jquery.megamenu.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/datetimepicker/ext/jquery.datetimepicker.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/daterangepicker/js/bootstrap-daterangepicker.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/daterangepicker/ext/jquery.daterangepicker.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/datepicker/ext/jquery.datepicker.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/jquery.core.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/definedTable/js/jquery.definedTable.extend.js",
		"<%= config.sources %>/scripts/jquery.fileUpload.plugin.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/confirmation/bootstrap-confirmation.min.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/vendor/load-image.min.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-image.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-audio.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-video.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/tabs/js/jquery.tabs.extends.js",
		"<%= config.sources %>/scripts/artTemplate/dist/template.js",
		"<%= config.sources %>/scripts/artTemplate/dataUtils.js",
		"<%= config.sources %>/scripts/jquery-validation/dist/jquery.validate.min.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/portlet/jquery.portlet.extends.js",
		"<%= config.sources %>/scripts/designer/animate.js",
		"<%= config.sources %>/scripts/designer/metro_render.js",
		"<%= config.sources %>/webfile/js/jquery.cookie.js",
		"<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/js/bootstrap-select.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js",
		"<%= config.sources %>/webfile/js/combine.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/definedCard/ext/jquery.definedCard.extend.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/definedPanel/ext/jquery.definedPanel.extend.js",
		"<%= config.sources %>/scripts/plugins/bootstrap/wslider/js/slideshow-jq.js"
	],
	// 定义平台 事件文件 打包列表
	extendFiles = [
		//"<%= config.sources %>/scripts/bootstrap.extend.all.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/flow.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/core.js",
		"<%= config.sources %>/scripts/kendo/utils.js",
		"<%= config.sources %>/scripts/kendo/base.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/validate.extend.js",
		"<%= config.sources %>/scripts/kendo/treelistbt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/pagebt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bindEvent.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/btalert.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/btmessenger.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/calendarbt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/datebt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/icheckbox.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/icheckradio.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/list.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/officebt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/login.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/megamenu.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/metroselect.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/mtbutton.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/progressbarbt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/textareabt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/textboxbt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/touchspin.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/selectpicker.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/ztree.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/nestable.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/ratylimaster.extend.js",
		"<%= config.sources %>/scripts/kendo/echarts.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/diagrambt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/editorbt.extend.js",
		"<%= config.sources %>/scripts/kendo/charts.extend.js",
		"<%= config.sources %>/scripts/kendo/video.extend.js",
		"<%= config.sources %>/scripts/kendo/gridbt.extend.js",
		"<%= config.sources %>/scripts/kendo/imageupload.extend.js",
		"<%= config.sources %>/scripts/kendo/custom.extend.js",
		"<%= config.sources %>/scripts/kendo/a.extend.js",
		"<%= config.sources %>/scripts/kendo/definedTable.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/step.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/label.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/jqfileupload.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/gantt.extend.js",
		"<%= config.sources %>/scripts/kendo/cell.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bootstraptabs.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bootstrapdialog.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/colmd.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/carousel.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/tagsinput.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/sidebar.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/sionalcode.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bim.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/gridList.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/excelUpload.extends.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/svgEditor.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bridgeseam.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/jplayer.extend.js",
		"<%= config.sources %>/scripts/kendo/jsgis.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/mobile.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/videoplay.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/definedCard.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/mapext.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/rangeSlider.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/fullpage.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/prompt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/popover.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/image.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/definedButton.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/editor.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/definedButton.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/wyvideo.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/form.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/webchat.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/phoneListView.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/webchat.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/alivideo.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/phoneTimePicker.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/searchbar.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/loadMore.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bmapext.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bmapddext.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/bmapmarker.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/cellWebCab.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/reviewArea.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/scan.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/dhxgantt.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/modelbim.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/levellist.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/foldlist.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/mswitch.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/tabbar.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/mdatepicker.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/vectorDraw.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/rangecalendar.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/loginmessagevalid.extend.js",
		"<%= config.sources %>/scripts/kendo/bootstrap/medialist.extend.js",
		//如果需要添加打包js 在以上添加
		"<%= config.sources %>/scripts/defineBootstrapUiInit.js"
	];
	

    grunt.initConfig({
 
        pkg : grunt.file.readJSON('package.json'),
		config:{
			sources : '../WebContent' ,
			dist : '../WebContent/scripts/exec',
		},
        uglify: {
            options:{
                stripBanners:true, 
				sourceMap:true,
                banner: '/*! <%=pkg.name%>-<%=pkg.version%>.js <%= grunt.template.today("yyyy-mm-dd") %> */\n' 
            },
            target: {
				files :{
					'<%= config.dist %>/js/<%=pkg.name%>-<%=pkg.version%>.min.js':jsFiles
				}
            },
			extent_target: {
				files :{
					'<%= config.dist %>/js/bootstrap.extend.all.min.js':extendFiles
				}
            }
        },
		cssmin: {
			options: {
				shorthandCompacting: false,
				roundingPrecision: -1
			},
			target: {
				files: {
					'<%= config.dist %>/css/<%=pkg.name%>-<%=pkg.version%>.min.css': cssFiles
				}
			}
		},
		concat: {
			baseCss: {
                src: cssFiles,
                dest: "<%= config.dist %>/css/<%=pkg.name%>-<%=pkg.version%>.css"
            },
			baseJs: {
                src: jsFiles,
                dest: "<%= config.dist %>/js/<%=pkg.name%>-<%=pkg.version%>.js"
            },
			extentJs: {
				src: extendFiles,
                dest: "<%= config.dist %>/js/bootstrap.extend.all.js"
			}
		},
		// 拷贝 相关字体图标资源文件
		copy: {
			simpleLineFont:{
				expand: true, 
				cwd: '<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/fonts', 
				src: '*', 
				dest: '<%= config.dist %>/css/fonts'
			},
			// components.min.css
			componentsImg: {
				expand: true, 
				cwd: '<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/img/', 
				src: '*', 
				dest: '<%= config.dist %>/img/'
			},
			// bootstrap.min.css
			bootstrapFont: {
				expand: true, 
				cwd: '<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/fonts/bootstrap/', 
				src: '*', 
				dest: '<%= config.dist %>/fonts/bootstrap/'
			},
			// font-awesome.min.css
			bootstrapFont: {
				expand: true, 
				cwd: '<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/fonts/', 
				src: '*', 
				dest: '<%= config.dist %>/fonts/'
			},
			// icheck/skins/all.css
			icheckMinimalPng: {
				expand: true, 
				cwd: '<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/minimal', 
				src: '*.png', 
				dest: '<%= config.dist %>/css/'
			},
			icheckPolarisPng: {
				expand: true, 
				cwd: '<%= config.sources %>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/polaris', 
				src: '*.png', 
				dest: '<%= config.dist %>/css/'
			},
			// treelistbt
			treelistbtImg: {
				expand: true, 
				cwd: '<%= config.sources %>/scripts/plugins/bootstrap/treelist/img', 
				src: '*', 
				dest: '<%= config.dist %>/img/'
			}
		},
		watch: {
			configFiles: {
				files: [ 'Gruntfile.js'],
				options: {
					reload: true
				}
			},
            build: {
                files: cssFiles.concat(jsFiles,extendFiles), 
                tasks: ['uglify','cssmin','copy'], 
                options: {
                    spawn: false,
                }
            }
        }
    });

	grunt.loadNpmTasks('grunt-contrib-concat');

    grunt.loadNpmTasks('grunt-contrib-uglify');

	grunt.loadNpmTasks('grunt-contrib-cssmin');

	grunt.loadNpmTasks('grunt-contrib-copy');

	grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default',['concat','uglify','cssmin','copy']);
 
	grunt.registerTask('watch-server',['default','watch']);

};