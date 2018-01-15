var pkg = require("./package.json");
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
// var ChunkManifestPlugin = require('chunk-manifest-webpack-plugin');
var OfflinePlugin = require('offline-plugin');
var ImageminPlugin = require('imagemin-webpack-plugin').default;
var BellOnBundlerErrorPlugin = require('bell-on-bundler-error-plugin');
// var FaviconsWebpackPlugin = require('favicons-webpack-plugin');
var BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
// var getStateEntries = require("./webpack.getStateEntries");
// var GenFutureStates = require("./webpack.GenFutureStates");

var args = require('yargs').argv;
var path = require('path');
// parameters
var env = args.env && args.env.env ? args.env.env.toUpperCase() : "DEV";
var isProd = "PROD" == env;
var outputPath = args.env && args.env.out ? args.env.out : "build";

var base = __dirname;
var appName = 'app';


var vendorCssPlugin = new ExtractTextPlugin({
    filename: isProd ? '[name].[hash].css' : '[name].css'
});
var appCssPlugin = new ExtractTextPlugin({
    filename: isProd ? '[name].[hash].scss.css' : '[name].scss.css'
});
var baseUrls = {
    PROD   : "./",
    TEST12 : "https://test13.kingsilk.xyz/test/12/platform/",
    TEST13 : "https://test13.kingsilk.xyz/test/13/platform/",
    TEST14 : "https://test13.kingsilk.xyz/test/14/platform/",
    DEV    : "https://test13.kingsilk.xyz/dev/71/platform/"
};
var baseUrl = baseUrls[env];
if(!baseUrl){
    console.log("baseUrl 未配置，终止");
    return;
}
const entry = {
    "index": [
        "./src/app/index.js"
    ],
    "commons": [
        "normalize-css/normalize.css",
        "animate.css/animate.css",
        // "swiper/dist/css/swiper.css",
        "angular",
        "angular-material",
        "angular-material/angular-material.css",
        "angular-ui-router",

        "ui-router-extras",
        'cropper/dist/cropper.css',
        'bootstrap/dist/css/bootstrap.css',
        'bootstrap/dist/css/bootstrap-theme.css',
        'bootstrap-datepicker/dist/css/bootstrap-datepicker3.standalone.css',
        'webuploader/dist/webuploader.css',
        "angular-material-sidenav/angular-material-sidenav.css",
        "angular-material-data-table/dist/md-data-table.css",
        "angular-hotkeys/build/hotkeys.css",
        "angular-ui-tree/dist/angular-ui-tree.css",

    ]
};
Object.assign(entry);

const plugins = [
    new BundleAnalyzerPlugin({
        analyzerMode: 'static',
        reportFilename: 'report.html',
        openAnalyzer: false,
        generateStatsFile: true,
        statsFilename: 'report.json',
    }),
    new BellOnBundlerErrorPlugin(),
    new webpack.BannerPlugin("copyright @ kingsilk.net"),
    new webpack.optimize.CommonsChunkPlugin({
        name: "commons",
        minChunks: 2
        //filename: isProd ? 'commons.[hash].js' : 'commons.js',
        //minChunks: 3,
        // children: true,
        // async: true,
    }),
    new webpack.DefinePlugin({
        __ENV__: JSON.stringify(env),
        __APP__: JSON.stringify(pkg.name),
        __VERSION__: JSON.stringify(pkg.version)
    }),
    vendorCssPlugin,
    appCssPlugin,



    new HtmlWebpackPlugin({
        template: './src/app/index.html',
        filename: '../index.html',
        chunks: ['commons', 'index'],
        favicon: 'favicon.ico',
        appName: appName,
        hash: !isProd,
        minify: {
            html5: true,
            removeComments: true,
            collapseWhitespace: true,
            preserveLineBreaks: true,
            minifyCSS: true
        },
        baseUrl: baseUrl
    }),

    // Make sure that the plugin is after any plugins that add images
    new ImageminPlugin({
        disable: !isProd,
        pngquant: {
            quality: '95-100'
        }
    })
];

if (isProd) {
    plugins.push(
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false
            },
            mangle: {
                except: ['$super', '$', 'exports', 'require']
            },
            output: {
                comments: false
            }
        }),
        new webpack.optimize.OccurrenceOrderPlugin()
    );
}

const config = {
    target: "web",
    externals: {
        jquery: 'window.$'
    },
    resolve: {
        modules: [
            'node_modules',
            'bower_components',
        ]
    },
    entry: entry,
    output: {
        path: path.resolve(base, `${outputPath}/${pkg.name}`),
        filename: isProd ? '[name].[chunkhash].js' : '[name].js',
        chunkFilename: isProd ? '[name].[chunkhash].chunk.js' : '[name].chunk.js',
        crossOriginLoading: "anonymous",
        sourceMapFilename: isProd ? "[id].[hash].[file].map" : '[file].map',
        publicPath: `./${pkg.name}/`
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: {
                            cacheDirectory: path.resolve(base, '.tmp')
                        }
                    }
                ],
                exclude: /node_modules/
            },

            {
                test: /src\/app\/states\/.*\.css$/,
                use: [
                    "style-loader",
                    {
                        loader: 'css-loader',
                        options: {
                            minimize: true,
                            sourcemap: false,
                            discardComments: {
                                removeAll: true
                            },
                            calc: false
                        }
                    }
                ],
            },
            {
                test: /src\/app\/.*\.scss$/,
                use: [
                    "style-loader",
                    {
                        loader: 'css-loader',
                        options: {
                            minimize: true,
                            sourcemap: false,
                            discardComments: {
                                removeAll: true
                            },
                            calc: false
                        }
                    },
                    "sass-loader"
                ],
            },

            {
                test: /(node_modules|src\/app\/ks-admin-font).*\.css$/,
                loader: vendorCssPlugin.extract({
                    fallback: 'style-loader',
                    use: {
                        loader: 'css-loader',
                        // XXX : 需要关注 https://github.com/webpack/css-loader/pull/400
                        options: {
                            minimize: true,
                            sourceMap: true,
                            discardComments: {
                                removeAll: true
                            },
                            calc: false
                        }

                    }
                })
            },
            {
                test: /(node_modules).*\.scss$/,
                loader: appCssPlugin.extract(
                    {
                        fallback: 'style-loader',
                        use: [
                            {
                                loader: 'css-loader',
                                // XXX : 需要关注 https://github.com/webpack/css-loader/pull/400
                                options: {
                                    minimize: true,
                                    sourceMap: true,
                                    discardComments: {
                                        removeAll: true
                                    },
                                    calc: false
                                }
                            },
                            {
                                loader: 'sass-loader',
                                options: {
                                    sourceMap: true
                                }
                            }
                        ]
                    })
            },
            {
                test: /\.(woff|woff2|ttf|eot|svg)(\?]?.*)?$/,
                use: [
                    {
                        loader: "file-loader",
                        options: {
                            name: "assets/fonts/[name]-[hash].[ext]",
                            publicPath: './'　//重新指定访问路径　　参考　https://github.com/webpack-contrib/file-loader
                        }
                    }
                ]
            },

            /*
            * 这种打包字体的方式会直接以hash的方式重命名
            * */
            // {
            //     test: /\.(woff|woff2|ttf|eot|svg)(\?]?.*)?$/,
            //     loader: 'file-loader?publicPath=./&outputPath=assets/fonts/'
            // },
            /*
            * 下面的方式可用
            * */
            // {
            //     test: /\.(woff|woff2|ttf|eot|svg)(\?]?.*)?$/,
            //     loader: 'file-loader',
            //     options: {
            //         name: "assets/fonts/[name]-[hash].[ext]",
            //         publicPath: './'
            //     }
            // },
            {
                test: /\.(jpg|jepg|png|gif)(\?]?.*)?$/,
                use: [
                    {
                        loader: "file-loader",
                        options: {
                            name: "assets/imgs/[name]-[hash].[ext]"
                        }
                    }
                ]
            }
        ]
    },
    plugins: plugins,
    devtool: "source-map",
    devServer: {
        contentBase: path.resolve(base, 'outputPath'),
        historyApiFallback: true,
        stats: {
            modules: false,
            cached: false,
            colors: true,
            chunk: false
        },
        host: '0.0.0.0',
        port: 8080,
        inline: true,
        hot: false,
        clientLogLevel: "info",
        compress: false,
        quiet: false
    },
};

module.exports = config;
