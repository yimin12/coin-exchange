const BOM = '\uFEFF';

// 先将二维数组转成纯文本，重点是要处理可能在内容中出现的分隔符和双引号：
export function arrayToCsv(data, args = {}) {
  let columnDelimiter = args.columnDelimiter || ',';
  let lineDelimiter = args.lineDelimiter || '\n';

  return data.reduce((csv, row) => {
    const rowContent = Array.isArray(row) ?
      row.reduce((rowTemp, col) => {
        let ret = rowTemp ? rowTemp + columnDelimiter : rowTemp;
        if (col) {
          let formatedCol = col.toString().replace(new RegExp(lineDelimiter, 'g'), ' ');
          ret += /,/.test(formatedCol) ? `"${formatedCol}"` : formatedCol;
        }
        return ret;
      }, '') :
      row;
    return (csv ? csv + lineDelimiter : '') + rowContent;
  }, '');
}

// 导出cvs文件
export function exportCsv(inputData, filename = 'export.csv') {
  // const csv = arrayToCsv(inputData);
  const csv = inputData;

  if (navigator.msSaveOrOpenBlob) {
    let blob = new Blob([BOM + csv], {
      type: 'text/csv;charset=utf-8;'
    });
    navigator.msSaveOrOpenBlob(blob, filename);
  } else {
    let uri = encodeURI(`data:text/csv;charset=utf-8,${BOM}${csv}`);
    let downloadLink = document.createElement('a');
    downloadLink.href = uri;
    downloadLink.download = filename;
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
  }
}
