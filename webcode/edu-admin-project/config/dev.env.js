'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  //BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  BASE_API: '"http://localhost:8080"',//后端项目的地址
  OSS_PATH: '"https://guli-file.oss-cn-beijing.aliyuncs.com"'//阿里云文件服务地址
})
