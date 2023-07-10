
export const validateHttpResponses = (res, target) => {
  if (res.status === 403) {
    return `Sul pole piisavalt õigusi, et kustutada!`;
  } else if (res.status === 400) {
    return `${target} ei ole kustutatav, see on tellimuses`;
  } else if (res.status === 204) {
    return `${target} ei ole kustutatav, ID on vale`;
  }
}