class Mall:
    def __init__(self,mall_name, mall_limit,store_name, store_limit):
        #Se calhar cada pessoa vai ter de ter um status, para vermos em q situacao esta (waiting_mall, waiting_store, inside_mall, inside_store)
        #Basicamente o atributo pacient, estaria ligado a uma pessoa e permite ver se a pessoa se importa ou nao de esperar na fila (mall ou store)
        #pacient = dont_want_to_wait/dont_mind_wait
        self.people_inside_mall = 0
        self.people_inside_store = 0
        self.people_waiting_mall = 0
        self.people_waiting_store = 0
        self.mall_limit = mall_limit
        self.mall_name = mall_name
        self.store_limit = store_limit
        self.store_name = store_name

    def enterMall(self):
        self.people_inside_mall += 1
        '''
        if pessoa.status == "waiting_mall":
            self.people_waiting_mall -= 1
            pessoa.status = "inside_mall"
        '''

    def enterStore(self):
        self.people_inside_store += 1
        '''
        if pessoa.status = "waiting_store":
            self.people_waiting_store -= 1
            pessoa.status = "inside_store"
        '''

    def exitMall(self):
        #quando sair do mall é pq ja nao tava em nenhuma loja
        self.people_inside_mall -= 1

        #Esta situação, acho q nao é preciso verificar pq assume-se q não se usa esta função quando não há ninguem no mall
        '''
        if self.people_inside_mall != 0:
            self.people_inside_mall -= 1
            #!Algum status para pessoas q ja nao estao no shopping e nao tao a espera?
            pessoa.status = "not_in_mall"
        else: 
            print("Qq tp de erro")
        '''
    
    def exitStore(self):
        #quando sai da loja, pode continuar no mall, apenas nao ta em nenhuma loja
        self.people_inside_store -= 1

        #Esta situação, acho q nao é preciso verificar pq assume-se q não se usa esta função quando não há ninguem na store
        '''
        if self.people_inside_store != 0:
            self.people_inside_store -= 1
            pessoa.status = "inside_mall"
        else: 
            print("Qq tp de erro")
        '''

    def waiting_outside_Mall(self):
        #quando sai da loja, pode continuar no mall, apenas nao ta em nenhuma loja
        #!So se o shopping estiver lotado e a pessoa respeitou
        self.people_waiting_mall += 1
        #pessoa.status = "waiting_mall"
        
    def waiting_outside_Store(self):
        #quando sai da loja, pode continuar no mall, apenas nao ta em nenhuma loja
        #!So se a loja estiver lotada e a pessoa respeitou
        self.people_waiting_store += 1   
        #pessoa.status = "waiting_store"

    '''
    def checkStatus(self, status, paciente):
        if paciente == "dont_want_to_wait" and status == "waiting_mall":
            self.people_waiting_mall -= 1
        elif paciente == "dont_want_to_wait" and status == "waiting_store":
            self.people_waiting_store -= 1
    '''


    def __str__(self) -> str:
        return "Mall limit - " + str(self.mall_limit) + " , Store Limit - " + str(self.store_limit) 


class Person: 
    def __init__(self, name , status, pacient):
        self.name = name
        self.status = None
        self.pacient = None
    
    def getStatus(self):
        return self.status
    
    def setStatus(self, status):
        self.status = status

    def isPacient(self):
        pass
    