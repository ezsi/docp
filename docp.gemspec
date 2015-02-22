Gem::Specification.new do |s|
  s.name        = 'docp'
  s.version     = '0.0.1'
  s.authors     = ['ezsi']
  s.email       = ['ezsias.bela@gmail.com ']
  s.homepage    = 'https://https://github.com/ezsi/docp/tree/ruby'
  s.summary     = 'Design of computer programs'
  s.description = 'Udacity course Ruby implementation.'

  s.files        = Dir[*%w[config data lib].map { |dir| dir + '/**/*'}]
  s.require_path = 'lib'
end
