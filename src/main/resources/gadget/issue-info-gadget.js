(function () {
  function ctx() {
    // pathname dạng: /jira/plugins/servlet/gadgets/ifr?...
    var p = window.location.pathname || "";
    var i = p.indexOf("/plugins/servlet/");
    return i > 0 ? p.substring(0, i) : "";
  }
  function esc(s){ return (s==null?"":String(s)) }

  var prefs = new gadgets.Prefs();
  var group = prefs.getString("group") || "";
  document.getElementById("group-label").textContent = group || "(All)";

  var url = ctx() + "/rest/jira-cloud-gadget/1.0/issues";
  var qs = "?limit=100" + (group ? "&group="+encodeURIComponent(group) : "");

  function render(items) {
    var html = '';
    html += '<table class="aui aui-table-sortable">';
    html += '<thead><tr>' +
            '<th>Key</th><th>Summary</th><th>urd</th><th>Epic link</th>' +
            '<th>IT main dept</th><th>IT relation dept</th><th>Assignee</th>' +
            '<th>Dev leader</th><th>Status</th><th>Dev start date</th>' +
            '<th>Dev member</th><th>Duedate</th><th>Pending Date</th>' +
            '<th>Manhours actual</th><th>Manhours estimate</th>' +
            '</tr></thead><tbody>';

    for (var i=0;i<items.length;i++){
      var r = items[i];
      html += '<tr>' +
        '<td>'+esc(r.key)+'</td>' +
        '<td title="'+esc(r.summary)+'">'+esc(r.summary)+'</td>' +
        '<td>'+esc(r.urd)+'</td>' +
        '<td>'+esc(r.epicLink)+'</td>' +
        '<td>'+esc(r.itMainDept)+'</td>' +
        '<td>'+esc(r.itRelationDev)+'</td>' +
        '<td>'+esc(r.assignee)+'</td>' +
        '<td>'+esc(r.devLeader)+'</td>' +
        '<td>'+esc(r.status)+'</td>' +
        '<td>'+esc(r.devStartDate)+'</td>' +
        '<td>'+esc(r.devMember)+'</td>' +
        '<td>'+esc(r.dueDate)+'</td>' +
        '<td>'+esc(r.pendingDate)+'</td>' +
        '<td>'+esc(r.manHoursActual)+'</td>' +
        '<td>'+esc(r.manHoursEstimate)+'</td>' +
        '</tr>';
    }
    html += '</tbody></table>';
    document.getElementById('table-wrap').innerHTML = html;
    if (gadgets && gadgets.window && gadgets.window.adjustHeight) {
      gadgets.window.adjustHeight();
    }
  }

  function fetchData() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url + qs, true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4) {
        if (xhr.status >= 200 && xhr.status < 300) {
          try {
            var json = JSON.parse(xhr.responseText);
            render(json.items || []);
          } catch (e) {
            document.getElementById('table-wrap').innerHTML =
              '<div class="aui-message aui-message-error">Parse error</div>';
          }
        } else {
          document.getElementById('table-wrap').innerHTML =
            '<div class="aui-message aui-message-error">HTTP '+xhr.status+'</div>';
        }
        if (gadgets && gadgets.window && gadgets.window.adjustHeight) {
          gadgets.window.adjustHeight();
        }
      }
    };
    xhr.send();
  }

  // Set title dynamically
  try {
    gadgets.window.setTitle("JIRA Issue Cloud (DB)");
  } catch(e){}

  fetchData();
})();
