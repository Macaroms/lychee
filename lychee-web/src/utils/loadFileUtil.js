export function loadFile(url) {
  const xhr = new XMLHttpRequest()
  const okStatus = document.location.protocol === 'file:' ? 0 : 200
  xhr.open('GET', "/WW_verify_S0tZA59MEWgInl15.txt", false)
  xhr.overrideMimeType('text/html;charset=utf-8')// 默认为utf-8
  xhr.send(null)
  return xhr.status === okStatus ? xhr.responseText : null
}