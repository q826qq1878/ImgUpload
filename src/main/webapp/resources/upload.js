//filePicker 点击上传图片按钮的ID
//imgId  回显的ID
//imgUrl 储存URL
function imgUpload(imgId){

    // 初始化Web Uploader
    var uploader = WebUploader.create({
        // 选完文件后，是否自动上传。
        auto: false,
        // swf文件路径
        swf:  '../resources/demo/Uploader.swf',
        // 文件接收服务端。
        server: 'http://localhost:8080/ImgUpload/upLoad/fileUpload',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#'+imgId+"_btn",
        //选择拖拽容器
        dnd: '#uploader-demo',
        //粘贴截图的图片
        paste: document.body,
        //拖拽功能开启
        disableGlobalDnd: true,
        //分片处理大图片 - 暂时关闭
        //chunked: true,

        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png'   //修改这行
        },
        formData: {
            uid: 123
        }
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader.makeThumb( file, function( error, src ) {
            $("#"+imgId+"_img").attr( 'src', src );
        }, 100, 100 );
        uploader.upload();
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            percent = $li.find('.progress span');

        // 避免重复创建
        if ( !percent.length ) {
            percent = $('<p class="progress"><span></span></p>')
                .appendTo( $li )
                .find('span');
        }
        percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file ,data) {
        doCallback(eval(imgId+"_callBack"),[data]);
    });

    function doCallback(fn,args)
    {
        fn.apply(this, args);
    }

    // 文件上传失败，显示上传出错。
    uploader.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');
        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }
        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });
}

