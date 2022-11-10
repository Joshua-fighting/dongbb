import HttpRequest from '@/lib/request'
export const backendServer = "http://localhost:8201"
export const jwtServerInstance = new HttpRequest(backendServer)
