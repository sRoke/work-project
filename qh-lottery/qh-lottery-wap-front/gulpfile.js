const gzip = require('gulp-gzip');
const pkg = require('./package.json');
const gulp = require('gulp');
const runSequence = require('run-sequence');
const fs = require('fs');
const concat = require('gulp-concat');
const minifyCss = require('gulp-minify-css');
const rename = require('gulp-rename');
const rev = require('gulp-rev');
const gulpif = require('gulp-if');
const uglify = require('gulp-uglify');
const less = require('gulp-less');
const minimist = require('minimist');
const plumber = require('gulp-plumber');
const htmlreplace = require('gulp-html-replace');
const del = require('del');
const htmlmin = require('gulp-htmlmin');
const babel = require('gulp-babel');
const tar = require('gulp-tar');

// env env=dev gulp package
var curEnv = process.env.env || 'dev';

console.log("---------------------- using env = " + curEnv);

var knownOptions = {
    string: 'env',
    default: {env: curEnv}
};
var options = minimist(process.argv.slice(2), knownOptions);

const config = {
    dev: {
        base: "https://test13.kingsilk.xyz/dev/63/qh/lottery/",
        src: "./"
    },
    test12: {
        base: "https://test13.kingsilk.xyz/test/12/qh/lottery/",
        src: "./"
    },
    test13: {
        base: "https://test13.kingsilk.xyz/test/13/qh/lottery/",
        src: "./"
    },
    test14: {
        base: "https://test13.kingsilk.xyz/test/14/qh/lottery/",
        src: "./"
    },
    prod: {
        base: "/qh/lottery/",
        src: ""
    }
};

//js源文件路径
const jsSrc = [
    `src/setting-${options.env}.js`,
    'src/controllers/*.js'
];

// assets路径
const appAssetsSrc = [
    'src/assets/**/*'
];

// 如果所选环境不存在
if (!config[options.env]) {
    // console.log(`${options.env}环境不存在，改为dev`);
    options.env = "dev"
}

// console.log(`使用环境：${options.env}`);

//清除项目
gulp.task('clean', cb => {
    return del([
        'target/**/*',
        'target/js/*.js',
        'target/lib/*.js',
        'target/css/*.css',
        'target/less/*.css',
        'target/*.html',
    ], cb);
});

gulp.task("assets", cb => {
    gulp.src(appAssetsSrc)
        .pipe(gulp.dest('./target/assets'))
        .on('finish', () => {
            cb();
        });
});
//合并html
gulp.task('html', cb => {
    gulp.src('./src/views/**/*.html')
        .pipe(gulp.dest('./target/'))
        .on('finish', () => {
            cb();
        });
});
// 合并js
gulp.task('js', cb => {
    gulp.src(jsSrc)
        .pipe(concat("app-min.js"))                             //js下所有的js文件 合并到app.js
        .pipe(babel({
            presets: ['env', 'es2015']                          //转es5
        }))
        //.pipe(gulpif(options.env == "prod", uglify()))          //仅在线上环境压缩
        .pipe(rev())                                            //加md5值
        .pipe(gulp.dest('./target/js'))
        .on('finish', () => {
            cb();
        });
});

// 迁移lib中的第三方js
gulp.task('libs', cb => {
    gulp.src('./lib/**/*.js')
        .pipe(concat("libs-min.js"))                            //js下所有的js文件 合并到libs.js
        .pipe(gulpif(options.env == "prod", uglify()))
        .pipe(rev())
        .pipe(gulp.dest('./target/lib'))
        .on('finish', () => {
            cb();
        });
});

//合并css
gulp.task('less', cb => {
    gulp.src('./src/css/app.scss')          //定义css统一入口app.scss文件
        .pipe(less())                       //合并
        .pipe(minifyCss({                   //混淆
            keepSpecialComments: 0
        }))
        .pipe(rename("app-min.css"))
        //.pipe(rename({extname: '.min.css'}))  //混淆后加后缀
        .pipe(rev())
        .pipe(gulp.dest('./target/css/'))       //混淆文件放入目标文件夹
        .on('finish', () => {
            cb();
        });
    //.on('end', done);
});


//合并css
gulp.task('thirdLess', cb => {
    gulp.src('./lib/**/*.css')          //定义css统一入口app.scss文件
        .pipe(less())                       //合并
        .pipe(minifyCss({                   //混淆
            keepSpecialComments: 0
        }))
        .pipe(rename("app-thirdMin.css"))
        .pipe(rev())
        .pipe(gulp.dest('./target/less/'))       //混淆文件放入目标文件夹
        .on('finish', () => {
            cb();
        });
    //.on('end', done);
});


//将css、js、lib加入到index.html
const indexHtmlSrc = "src/views/index.html";
gulp.task('add', cb => {
    var appThirdCss = fs.readdirSync("target/less").find(ele => /^app-thirdMin-\w+\.css$/.test(ele));
    var appCss = fs.readdirSync("target/css").find(ele => /^app-min-\w+\.css$/.test(ele));
    var libJs = fs.readdirSync("target/lib").find(ele => /^libs-min-\w+\.js$/.test(ele));
    var appJs = fs.readdirSync("target/js").find(ele => /^app-min-\w+\.js$/.test(ele));
    gulp.src(indexHtmlSrc)
        .pipe(plumber())
        .pipe(htmlreplace({
            'base': {src: null, tpl: `<base href="${config[options.env].base}"/>`},
            'appCss': {src: null, tpl: `<link rel="stylesheet" href="${config[options.env].src}css/${appCss}"/>`},
            'appThirdCss': {
                src: null,
                tpl: `<link rel="stylesheet" href="${config[options.env].src}less/${appThirdCss}"/>`
            },
            'libJs': {src: null, tpl: `<script src="${config[options.env].src}lib/${libJs}"></script>`},
            'appJs': {src: null, tpl: `<script src="${config[options.env].src}js/${appJs}"></script>`}
        }))
        // .pipe(htmlmin({
        //     collapseWhitespace: true,
        //     preserveLineBreaks: true
        // }))
        .pipe(gulp.dest('target'))
        .on('finish', () => {
            cb();
        });
});

//合并html
gulp.task('html', cb => {
    gulp.src('./src/views/**/*.html')
        .pipe(gulp.dest('./target/'))
        .on('finish', () => {
            cb();
        });
});

gulp.task('app.compile', cb => {
    console.log("--------------------------app.compile");
    runSequence(
        "clean",
        [
            "assets",
            "js",
            "less",
            "html",
            "libs",
            "thirdLess"
        ],
        "add",
        () => {
            cb();
        });
});

//打包
gulp.task('package', cb => {
    runSequence(
        "app.compile",
        () => {
            gulp.src([
                "target/**/*",
                "!target/**/*.tar",
            ])
                .pipe(rename(p => {
                    p.dirname = pkg.name + "/" + p.dirname;
                }))
                .pipe(tar(`${pkg.name}.tar`))
                .pipe(gzip())
                .pipe(gulp.dest("./target"))
                .on('finish', () => {
                    cb();
                });
        });
});

//启动
gulp.task('default', function () {
    //将你的默认的任务代码放在这里
    gulp.run("app.compile");
    // 监听文件变化
    gulp.watch(['./src/controllers/*.js', './src/css/**/*.scss', './views/*.html', './src/assets/*'], function () {
        gulp.run('app.compile');
    });
});
