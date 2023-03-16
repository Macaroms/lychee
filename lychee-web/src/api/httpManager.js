import { axios } from '@/utils/request'

export function getAction (url, parameter) {
  return axios({
    url: url,
    method: 'get',
    params: parameter
  })
}

export function postAction (url, parameter) {
  return axios({
    url: url,
    method: 'post',
    data: parameter
  })
}

export function putAction (url, parameter) {
  return axios({
    url: url,
    method: 'put',
    data: parameter
  })
}

export function deleteAction (url, parameter) {
  return axios({
    url: url,
    method: 'delete',
    data: parameter
  })
}

export function httpAction (url, method, parameter) {
  return axios({
    url: url,
    method: method,
    data: parameter
  })
}