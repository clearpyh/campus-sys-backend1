const apiBase = '';
const out = document.getElementById('output');
const tokenStatus = document.getElementById('tokenStatus');

function setToken(token) { localStorage.setItem('token', token || ''); renderTokenStatus(); }
function getToken() { return localStorage.getItem('token') || ''; }
function renderTokenStatus() { tokenStatus.textContent = getToken() ? '已登录' : '未登录'; }
function show(data) { out.textContent = typeof data === 'string' ? data : JSON.stringify(data, null, 2); }

async function req(path, options = {}) {
  const headers = options.headers || {};
  if (!headers['Content-Type'] && options.body) headers['Content-Type'] = 'application/json';
  const t = getToken();
  if (t) headers['Authorization'] = 'Bearer ' + t;
  const res = await fetch(apiBase + path, { ...options, headers });
  if (!res.ok) { const text = await res.text(); throw new Error(res.status + ' ' + text); }
  const ct = res.headers.get('content-type') || '';
  if (ct.includes('application/json')) return res.json();
  return res.text();
}

document.getElementById('btnLogin').onclick = async () => {
  try {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const data = await req('/auth/login', { method: 'POST', body: JSON.stringify({ username, password }) });
    setToken(data.token);
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnLogout').onclick = () => { setToken(''); show('已登出'); };

document.getElementById('btnActCreate').onclick = async () => {
  try {
    const name = document.getElementById('actName').value.trim();
    const title = document.getElementById('actTitle').value.trim();
    const description = document.getElementById('actDesc').value.trim();
    const term = document.getElementById('actTerm').value.trim();
    const grade = document.getElementById('actGrade').value.trim();
    const body = { name, title };
    if (description) body.description = description;
    if (term) body.term = term;
    if (grade) body.grade = grade;
    const data = await req('/api/activities', { method: 'POST', body: JSON.stringify(body) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnPublish').onclick = async () => {
  try {
    const id = document.getElementById('publishId').value.trim();
    const data = await req('/api/activities/' + id + '/publish', { method: 'POST' });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnActQuery').onclick = async () => {
  try {
    const status = document.getElementById('actStatus').value;
    const name = document.getElementById('actSearchName').value.trim();
    const qs = new URLSearchParams();
    if (status) qs.append('status', status);
    if (name) qs.append('name', name);
    const data = await req('/api/activities' + (qs.toString() ? '?' + qs.toString() : ''));
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnClsCreate').onclick = async () => {
  try {
    const name = document.getElementById('clsName').value.trim();
    const activityId = Number(document.getElementById('clsActivityId').value.trim());
    const data = await req('/api/classrooms', { method: 'POST', body: JSON.stringify({ name, activityId }) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnClsAddStudents').onclick = async () => {
  try {
    const id = document.getElementById('clsIdForStudents').value.trim();
    const ids = document.getElementById('studentIds').value.split(',').map(s => Number(s.trim())).filter(n => !isNaN(n));
    const data = await req('/api/classrooms/' + id + '/students', { method: 'POST', body: JSON.stringify({ ids }) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnClsSetTeachers').onclick = async () => {
  try {
    const id = document.getElementById('clsIdForTeachers').value.trim();
    const ids = document.getElementById('teacherIds').value.split(',').map(s => Number(s.trim())).filter(n => !isNaN(n));
    const data = await req('/api/classrooms/' + id + '/teachers', { method: 'POST', body: JSON.stringify({ ids }) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnTeacherAdd').onclick = async () => {
  try {
    const name = document.getElementById('tName').value.trim();
    const email = document.getElementById('tEmail').value.trim();
    const phone = document.getElementById('tPhone').value.trim();
    const data = await req('/api/teachers', { method: 'POST', body: JSON.stringify({ name, email, phone }) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnStudentAdd').onclick = async () => {
  try {
    const name = document.getElementById('sName').value.trim();
    const grade = document.getElementById('sGrade').value.trim();
    const data = await req('/api/students', { method: 'POST', body: JSON.stringify({ name, grade }) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnStudentAudit').onclick = async () => {
  try {
    const id = document.getElementById('sAuditId').value.trim();
    const data = await req('/api/students/' + id + '/audit', { method: 'POST' });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnCourseCreate').onclick = async () => {
  try {
    const name = document.getElementById('courseName').value.trim();
    const activityId = Number(document.getElementById('courseActivityId').value.trim());
    const classroomIdRaw = document.getElementById('courseClassroomId').value.trim();
    const classroomId = classroomIdRaw ? Number(classroomIdRaw) : null;
    const teacherId = Number(document.getElementById('courseTeacherId').value.trim());
    const data = await req('/api/courses', { method: 'POST', body: JSON.stringify({ name, activityId, classroomId, teacherId }) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnUnitCreate').onclick = async () => {
  try {
    const courseId = Number(document.getElementById('unitCourseId').value.trim());
    const chapter = document.getElementById('unitChapter').value.trim();
    const section = document.getElementById('unitSection').value.trim();
    const docUrl = document.getElementById('unitDocUrl').value.trim();
    const videoUrl = document.getElementById('unitVideoUrl').value.trim();
    const points = Number(document.getElementById('unitPoints').value.trim());
    const body = { chapter, section, points };
    if (docUrl) body.docUrl = docUrl;
    if (videoUrl) body.videoUrl = videoUrl;
    const data = await req('/api/units/course/' + courseId, { method: 'POST', body: JSON.stringify(body) });
    show(data);
  } catch (e) { show(e.message); }
};

document.getElementById('btnUnitQuery').onclick = async () => {
  try {
    const courseId = Number(document.getElementById('unitQueryCourseId').value.trim());
    const data = await req('/api/units/course/' + courseId);
    show(data);
  } catch (e) { show(e.message); }
};

renderTokenStatus();