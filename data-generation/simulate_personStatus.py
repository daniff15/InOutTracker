from enum import Enum

class Status(Enum):
    inside_mall = 1
    inside_store = 2
    waiting_mall = 3
    waiting_store = 4
    out_mall = 5

class Mall:
    def __init__(self,mall_name, mall_limit,stores):
        self.people_inside_mall = 0
        self.people_waiting_mall = 0
        self.mall_limit = mall_limit
        self.mall_name = mall_name
        self.stores = stores

    def enterMall(self):
        #verificar se se pode entrar por causa do limite (verificar tambem se a pessoa respeitou ou nao)
        self.people_inside_mall += 1

    def exitMall(self):
        #verficiar se ha gente no mall
        self.people_inside_mall -= 1
    
    def waiting_outside_Mall(self):
        #verificar se o limit foi atingido
        self.people_waiting_mall += 1

    def stop_waiting_outside_Mall(self):
        self.people_waiting_mall -= 1
        #verificar se ha gente a espera

class Store:
    def __init__(self, store_name, store_limit):
        self.store_name = store_name
        self.store_limit = store_limit
        self.people_inside_store = 0
        self.people_waiting_store = 0

    def enterStore(self):
        self.people_inside_store += 1
    
    def exitStore(self):
        self.people_inside_store -= 1

    def waiting_outside_Store(self):
        self.people_waiting_store += 1 

class Person:
    
    def __init__(self, name, status):
        self.name = name
        self.status = status
        self.paciente = None

    def change_status(self, status):
        self.status = status

    def stat_Paciente(self, paciente):
        self.paciente = paciente
    
