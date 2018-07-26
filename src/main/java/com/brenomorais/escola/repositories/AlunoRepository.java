package com.brenomorais.escola.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.brenomorais.escola.models.Aluno;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Repository
public class AlunoRepository {

	@Autowired
	private ConnectionDataBase conexao;

	public void salvar(Aluno aluno) {
		conexao.CreateConnection();
		
		MongoCollection<Aluno> alunos = this.conexao.getMongoDataBase().getCollection("alunos", Aluno.class);
		
		if(aluno.getId() == null) {
			alunos.insertOne(aluno);
		}else {
			alunos.updateOne(Filters.eq("_id", aluno.getId()), new Document("$set", aluno));
		}
		
		conexao.CloseConnection();
	}

	public List<Aluno> obterTodosAlunos() {

		conexao.CreateConnection();
		
		MongoCollection<Aluno> alunos = this.conexao.getMongoDataBase().getCollection("alunos", Aluno.class);
		MongoCursor<Aluno> resultado = alunos.find().iterator();

		List<Aluno> alunosEncontrados = popularAlunos(resultado);
		
		conexao.CloseConnection();

		return alunosEncontrados;		
		
	}
	
	public Aluno obterAlunoPor(String id) {

		conexao.CreateConnection();

		MongoCollection<Aluno> alunos = this.conexao.getMongoDataBase().getCollection("alunos", Aluno.class);
		Aluno aluno = alunos.find(Filters.eq("_id", new ObjectId(id))).first();
		
		conexao.CloseConnection();
		
		return aluno;
	}
	
	public List<Aluno> pesquisarPorNome(String nome) {
		
		conexao.CreateConnection();
		
		MongoCollection<Aluno> alunosCollection = this.conexao.getMongoDataBase().getCollection("alunos", Aluno.class);
		MongoCursor<Aluno> resultados = alunosCollection.find(Filters.eq("nome", nome), Aluno.class).iterator();
		
		List<Aluno> alunos = popularAlunos(resultados);
		
		this.conexao.getMongoClient().close();
		
		conexao.CloseConnection();		

		return alunos;		
	}
	
	private List<Aluno> popularAlunos(MongoCursor<Aluno> resultados) {

		List<Aluno> alunos = new ArrayList<>();

		while (resultados.hasNext()) {
			alunos.add(resultados.next());
		}			
		
		return alunos;
	}
}
